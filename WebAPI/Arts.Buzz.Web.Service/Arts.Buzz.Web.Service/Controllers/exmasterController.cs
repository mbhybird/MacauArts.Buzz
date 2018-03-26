using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class exmasterController : BaseApiController
    {
        // GET api/exmaster
        public IEnumerable<exmaster> Getexmasters()
        {
            return db.exmaster.AsEnumerable();
        }

        // GET api/exmaster/5
        public exmaster Getexmaster(string id)
        {
            exmaster exmaster = db.exmaster.Find(id);
            if (exmaster == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return exmaster;
        }

        // PUT api/exmaster/5
        public HttpResponseMessage Putexmaster(string id, exmaster exmaster)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != exmaster.extag)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(exmaster).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/exmaster
        public HttpResponseMessage Postexmaster(exmaster exmaster)
        {
            if (ModelState.IsValid)
            {
                db.exmaster.Add(exmaster);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, exmaster);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = exmaster.extag }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/exmaster/5
        public HttpResponseMessage Deleteexmaster(string id)
        {
            exmaster exmaster = db.exmaster.Find(id);
            if (exmaster == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.exmaster.Remove(exmaster);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, exmaster);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}